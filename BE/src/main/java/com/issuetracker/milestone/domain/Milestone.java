package com.issuetracker.milestone.domain;

import java.time.LocalDate;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Milestone {

	private Long id;
	private String title;
	private String description;
	private LocalDate deadline;
	private boolean isOpen;
	private boolean isDeleted;

	@Builder
	private Milestone(Long id, String title, String description, LocalDate deadline, boolean isOpen, boolean isDeleted) {
		this.id = id;
		this.title = title;
		this.description = description;
		this.deadline = deadline;
		this.isOpen = isOpen;
		this.isDeleted = isDeleted;
	}

	public static Milestone createInstanceById(Long id) {
		return Milestone.builder()
			.id(id)
			.build();
	}
}
