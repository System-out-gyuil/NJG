package com.example.demo.recipe.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class RecipeListResponse {
    @JsonProperty("RCP_SEQ")
    private String rcpSeq;
    
    @JsonProperty("RCP_NM")
    private String rcpNm;
    
    @JsonProperty("RCP_WAY2")
    private String rcpWay2;
    
    @JsonProperty("RCP_PAT2")
    private String rcpPat2;
    
    @JsonProperty("ATT_FILE_NO_MAIN")
    private String attFileNoMain;
    
    @JsonProperty("INFO_ENG")
    private String infoEng;
    
    @JsonProperty("RCP_PARTS_DTLS")
    private String rcpPartsDtls;
}

