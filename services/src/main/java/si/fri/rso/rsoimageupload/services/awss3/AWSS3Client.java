package si.fri.rso.rsoimageupload.services.awss3;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.rekognition.AmazonRekognitionClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import si.fri.rso.rsoimageupload.config.AppProperties;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.io.File;

@ApplicationScoped
public class AWSS3Client {

    @Inject
    private AppProperties appProperties;

    private AmazonS3 s3client;
    private String bucketName = "rso-komicarmin";

    @PostConstruct
    private void init() {

        AWSCredentials credentials;
        try {
            credentials = new BasicAWSCredentials(
                    appProperties.getAWSS3AccessKey(),
                    appProperties.getAWSS3SecretKey());
        } catch (Exception e) {
            throw new AmazonClientException("Cannot initialise the credentials.", e);
        }

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
