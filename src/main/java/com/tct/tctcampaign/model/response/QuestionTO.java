package com.tct.tctcampaign.model.response;

import com.tct.tctcampaign.model.db.Response;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QuestionTO {
    private Integer questionId;
    @NotBlank
    @NotNull
    private String questionName;
    @NotBlank
    @NotNull
    private String questionDesc;

    private String responseType;
    private String createdBy;
    private List<Response> response;
    private String comments;
    private String statusDesc;

}
