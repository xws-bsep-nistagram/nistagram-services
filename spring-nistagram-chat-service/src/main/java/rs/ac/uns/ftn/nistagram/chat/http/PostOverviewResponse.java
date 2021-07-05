package rs.ac.uns.ftn.nistagram.chat.http;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostOverviewResponse {

    private long id;
    private String author;
    private String caption;
    private List<String> mediaUrls;

}
