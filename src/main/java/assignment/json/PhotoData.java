package assignment.json;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PhotoData {

    @JsonAlias({"id"})
    private String photoId;

    @JsonAlias({"link"})
    private String link;

    @JsonAlias({"deletehash"})
    private String deletionHash;
    
	public String getPhotoId() {
		return photoId;
	}

	public void setPhotoId(String photoId) {
		this.photoId = photoId;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getDeletionHash() {
		return deletionHash;
	}

	public void setDeletionHash(String deletionHash) {
		this.deletionHash = deletionHash;
	}

    
    
    }
