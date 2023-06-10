package assignment.controller;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;


import assignment.json.PhotoData;
import assignment.services.ImageService;

@RestController
@RequestMapping("/images")
public class ImageController {
   

    @Autowired
    private ImageService imageService;
    private static final Logger LOGGER = LoggerFactory.getLogger(ImageController.class);

    @GetMapping("image/{id}")
    public ResponseEntity<?> getImage(@PathVariable("id") String id) throws Exception {
      LOGGER.info("getImage called for id {}", id);
      String link=imageService.getImage(id);
      return ResponseEntity.status(HttpStatus.OK).body("Please access you photo here "+link);
    }
    
    @DeleteMapping("image/{id}")
    public ResponseEntity<?>  deleteImage(@PathVariable("id") String id) throws Exception {
      LOGGER.info("delete called for id", id);
      imageService.deleteImage(id);
      return ResponseEntity.status(HttpStatus.OK).body("Photo has been deleted");
    }
    
    @PostMapping("image")
    public ResponseEntity<?>  uploadImage(@RequestParam("image") MultipartFile image) throws Exception {
    	 LOGGER.info("Upload image called");
     PhotoData data=imageService.uploadImage(image);
     return new ResponseEntity<PhotoData>(data, HttpStatus.OK);
    	}
    }
    

