package assignment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import assignment.entity.UserPhotoRecord;

public interface UserPhotoRecordRepository extends JpaRepository<UserPhotoRecord,Long> {
/*	  @Query(value = "DELETE FROM UserPhotoRecord e WHERE deletionHash = :deletionHash")      
	    int deleteByImageId(@Param("id") String deletionHash);*/
		
	 int deleteUserPhotoRecordBydeletionHash(String deletionHash); 

}
