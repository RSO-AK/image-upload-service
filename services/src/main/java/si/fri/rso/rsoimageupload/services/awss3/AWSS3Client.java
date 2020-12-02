package si.fri.rso.rsoimageupload.services.awss3;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;

import java.io.File;

public class AWSS3Client {
    AmazonS3 s3client;
    String bucketName = "rso-komicarmin";

    public AWSS3Client() {
        AWSCredentials credentials = new BasicAWSCredentials(
                "AKIAI4W3EB6D5443Q3JA",
                "8hQE7AsThOgD2KXwICIzCMOUpvCuI/UXzlhU1MrB"
        );

        s3client = AmazonS3ClientBuilder
                .standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withPathStyleAccessEnabled(true)
                .withRegion(Regions.EU_WEST_2)
                .build();
    }

    private void createBucket() {
        if(!s3client.doesBucketExist(bucketName)) {
            s3client.createBucket(bucketName);
        }
    }

    public String uploadObject(File image) {
        try {
            s3client.putObject(new PutObjectRequest(
                    bucketName,
                    image.getName(),
                    image).withCannedAcl(CannedAccessControlList.PublicRead)
            );
            return String.valueOf(s3client.getUrl(bucketName, image.getName()));
        } catch (AmazonServiceException e) {
            System.err.println(e.getErrorMessage());
            return null;
        }
    }
}
