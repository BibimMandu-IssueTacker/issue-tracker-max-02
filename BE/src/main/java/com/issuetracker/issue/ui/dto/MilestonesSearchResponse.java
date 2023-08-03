package com.issuetracker.issue.ui.dto;

import java.util.List;

import com.issuetracker.issue.application.dto.MilestoneSearchInformation;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class MilestonesSearchResponse {

	private List<MilestoneSearchResponse> milestones;

	public static MilestonesSearchResponse from(List<MilestoneSearchInformation> milestoneSearchResponses) {
		return new MilestonesSearchResponse(MilestoneSearchResponse.from(milestoneSearchResponses));
	}
}
