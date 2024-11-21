package uz.akbar.giybat.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/** AppResponse */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppResponse {

    private boolean success;

    private Object data;

    public AppResponse(Object data) {
        this.data = data;
    }
}
