package phong.com.example.project3.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseDTO {
    private int code;
    private String msg;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer totalPage;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Long count;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Object data;

    public ResponseDTO(int code, String msg) {
        super();
        this.code = code;
        this.msg = msg;
    }

}
