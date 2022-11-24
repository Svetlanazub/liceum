package pl.zubkova.webuniversity.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/**
 * @author Svetlana_Zubkova
 */
@Data
@Builder
@AllArgsConstructor
public class SortingDto {
    private SortingField sortingField;
    private SortingOrder sortingOrder;
}
