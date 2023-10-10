package com.nrt.quiz.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchPaginationRequest {
    private Long id;
    private Long categoryId;
    private String name;
    private String title;
    private Boolean active;
    private Integer per_page_record;
    private Integer page;

}
