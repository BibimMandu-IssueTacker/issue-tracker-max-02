package com.issuetracker.label.infrastructure;

import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.issuetracker.label.domain.Label;
import com.issuetracker.label.domain.LabelCountMetadata;
import com.issuetracker.label.domain.LabelRepository;

@Repository
public class JdbcLabelRepository implements LabelRepository {

	private static final String EXIST_BY_ID_SQL = "SELECT EXISTS(SELECT 1 FROM label WHERE id = :id AND is_deleted = false)";
	private static final String EXIST_BY_IDS_SQL = "SELECT IF(COUNT(id) = :size, TRUE, FALSE) FROM label WHERE is_deleted = false AND id IN(:labelIds)";
	private static final String SAVE_SQL = "INSERT INTO label(title, description, color) VALUE(:title, :description, :color)";
	private static final String UPDATE_SQL = "UPDATE label SET title = :title, description = :description, color = :color WHERE id = :id";
	private static final String DELETE_SQL = "UPDATE label SET is_deleted = true WHERE id = :id";
	private static final String FIND_ALL_SQL = "SELECT id, title, description, color FROM label WHERE is_deleted = false ORDER BY id desc";
	private static final String CALCULATE_COUNT_METADATA
		= "SELECT "
		+ "    (SELECT COUNT(id) FROM label WHERE is_deleted = false) AS total_label_count, "
		+ "    (SELECT COUNT(id) FROM milestone WHERE is_deleted = false) AS total_milestone_count";

	private final NamedParameterJdbcTemplate jdbcTemplate;

	public JdbcLabelRepository(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
	}

	@Override
	public boolean existById(Long id) {
		return jdbcTemplate.queryForObject(EXIST_BY_ID_SQL, Map.of("id", id), Boolean.class);
	}

	@Override
	public boolean existByIds(List<Long> labelIds) {
		SqlParameterSource params = new MapSqlParameterSource()
			.addValue("labelIds", labelIds)
			.addValue("size", labelIds.size());
		return jdbcTemplate.queryForObject(EXIST_BY_IDS_SQL, params, Boolean.class);
	}

	@Override
	public Long save(Label label) {
		MapSqlParameterSource param = new MapSqlParameterSource()
			.addValue("title", label.getTitle())
			.addValue("description", label.getDescription())
			.addValue("color", label.getColor());
		KeyHolder keyHolder = new GeneratedKeyHolder();

		jdbcTemplate.update(SAVE_SQL, param, keyHolder);
		return keyHolder.getKey().longValue();
	}

	@Override
	public int update(Label label) {
		MapSqlParameterSource param = new MapSqlParameterSource()
			.addValue("id", label.getId())
			.addValue("title", label.getTitle())
			.addValue("description", label.getDescription())
			.addValue("color", label.getColor());

		return jdbcTemplate.update(UPDATE_SQL, param);
	}

	@Override
	public int delete(Label label) {
		Map<String, Long> param = Map.of("id", label.getId());

		return jdbcTemplate.update(DELETE_SQL, param);
	}

	@Override
	public List<Label> findAll() {
		return jdbcTemplate.query(FIND_ALL_SQL, LABEL_ROW_MAPPER);
	}

	@Override
	public LabelCountMetadata calculateMetadata() {
		return jdbcTemplate.queryForObject(CALCULATE_COUNT_METADATA, Map.of(), LABEL_METADATA_MAPPER);
	}

	private static final RowMapper<Label> LABEL_ROW_MAPPER = (rs, rowNum) ->
		Label.builder()
			.id(rs.getLong("id"))
			.title(rs.getString("title"))
			.description(rs.getString("description"))
			.color(rs.getString("color"))
			.build();

	private static final RowMapper<LabelCountMetadata> LABEL_METADATA_MAPPER = (rs, rowNum) ->
		LabelCountMetadata.builder()
			.totalLabelCount(rs.getInt("total_label_count"))
			.totalMilestoneCount(rs.getInt("total_milestone_count"))
			.build();
}
