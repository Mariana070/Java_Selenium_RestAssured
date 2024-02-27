package pet_store.pojo;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Setter
@Getter
public class PetstorePojo {
    private int id;
    private String name;
    private List<String> photoUrls;
    private List<Object> tags;
    private String status;
    private pet_store.pojo.PetstoreCategoryPojo category;

}

