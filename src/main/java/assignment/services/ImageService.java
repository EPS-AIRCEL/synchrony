package assignment.services;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;

import assignment.entity.UserPhotoRecord;
import assignment.json.PhotoData;
import assignment.repository.UserPhotoRecordRepository;
import assignment.ssl.SSLFix;

@Service
public class ImageService {

  private static final String IMGUR_CLIENT_ID = "bd18b0897a362ee";

  private static final String IMAGE_CRUD = "https://api.imgur.com/3/image";

  private static final Logger LOGGER = LoggerFactory.getLogger(ImageService.class);
  @Autowired
  UserPhotoRecordRepository photoRepository;
  
  ObjectMapper mapper = new ObjectMapper();
  
  @Cacheable("image")
  public String getImage(String id) throws Exception {
	  
	  Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	  if (principal instanceof UserDetails) {
	    String username = ((UserDetails)principal).getUsername();
	  } else {
	    String username = principal.toString();
	  }
	  
	  
    LOGGER.info("getImage called for id {}", id);
    SSLFix.execute();
    RestTemplate restTemplate = new RestTemplate();
    
    HttpHeaders headers = new HttpHeaders();
	headers.set("Authorization", "Client-ID bd18b0897a362ee");
	
	HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);
    
    String url = IMAGE_CRUD+"/"+id;
    ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity,String.class);
	JSONObject jsonObject = new JSONObject(response.getBody());
	JSONObject myResponse = jsonObject.getJSONObject("data");
    PhotoData photoDetails = mapper.readValue(myResponse.toString(), PhotoData.class);
      return null;
  }
  
  
  @Transactional
  public Object deleteImage(String id) throws Exception {
    LOGGER.info("delete called for id {}", id);
    SSLFix.execute();
    RestTemplate restTemplate = new RestTemplate();
    HttpHeaders headers = new HttpHeaders();
	headers.set("Authorization", "Client-ID bd18b0897a362ee");
	HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);
    String url = IMAGE_CRUD+"/"+id;
    ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.DELETE, entity,String.class);
	photoRepository.deleteUserPhotoRecordBydeletionHash(id);
    return null;
  }
  
  @Transactional
  public Object uploadImage(MultipartFile image) throws Exception {
	  
	  Path tempFile = Files.createTempFile(null, null);
	  
	  Files.write(tempFile, image.getBytes());
	  File fileToSend = tempFile.toFile(); 
	  
    SSLFix.execute();
    RestTemplate restTemplate = new RestTemplate();
    MultiValueMap<String, Object> body
    = new LinkedMultiValueMap<>();
    body.add("image",  new FileSystemResource(fileToSend));
    HttpHeaders headers = new HttpHeaders();
	headers.set("Authorization", "Client-ID bd18b0897a362ee");
	 headers.setContentType(new MediaType("multipart", "form-data"));
	 HttpEntity<MultiValueMap<String, Object>> requestEntity
	 = new HttpEntity<>(body, headers);
    String url = IMAGE_CRUD;
    ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity,String.class);
    JSONObject jsonObject = new JSONObject(response.getBody());
	JSONObject myResponse = jsonObject.getJSONObject("data");
    PhotoData photoDetails = mapper.readValue(myResponse.toString(), PhotoData.class);
    UserPhotoRecord record = new UserPhotoRecord();
    record.setUserId(2l);
    BeanUtils.copyProperties(photoDetails, record);
    photoRepository.save(record);
    return null;
  }
}
