package com.example.demo.recipe.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import java.util.List;

@Data
public class ApiResponse<T> {
    @JsonProperty("COOKRCP01")
    private CookRcpData<T> cookRcpData;
    
    @Data
    public static class CookRcpData<T> {
        @JsonProperty("total_count")
        private String totalCount;
        
        @JsonProperty("row")
        private List<T> row;
        
        @JsonProperty("RESULT")
        private Result result;
    }
    
    @Data
    public static class Result {
        @JsonProperty("MSG")
        private String msg;
        
        @JsonProperty("CODE")
        private String code;
    }
}

