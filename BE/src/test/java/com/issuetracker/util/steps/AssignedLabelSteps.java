package com.issuetracker.util.steps;

import org.springframework.http.MediaType;

import com.issuetracker.issue.ui.dto.assignedlabel.AssignedLabelCreateRequest;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;

public class AssignedLabelSteps {

	public static ExtractableResponse<Response> 이슈에_등록_되어있는_라벨_목록_조회_요청() {
		return RestAssured.given().log().all()
			.accept(MediaType.APPLICATION_JSON_VALUE)
			.when().get("/api/issues/labels")
			.then().log().all().extract();
	}

	public static ExtractableResponse<Response> 이슈에_라벨_등록_요청(Long id, Long labelId) {
		return RestAssured.given().log().all()
			.body(new AssignedLabelCreateRequest(labelId))
			.contentType(MediaType.APPLICATION_JSON_VALUE)
			.accept(MediaType.APPLICATION_JSON_VALUE)
			.when().post("/api/issues/{id}/assigned-labels", id)
			.then().log().all().extract();
	}

	public static ExtractableResponse<Response> 이슈에_라벨_삭제_요청(Long id, Long assignedLabelId) {
		return RestAssured.given().log().all()
			.accept(MediaType.APPLICATION_JSON_VALUE)
			.when().delete("/api/issues/{id}/assigned-labels/{assigned-label-id}", id, assignedLabelId)
			.then().log().all().extract();
	}
}
