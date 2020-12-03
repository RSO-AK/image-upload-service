package si.fri.rso.rsoimageupload.config;

import com.kumuluz.ee.configuration.cdi.ConfigBundle;
import com.kumuluz.ee.configuration.cdi.ConfigValue;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
@ConfigBundle("app-properties")
public class AppProperties {

    @ConfigValue(value = "awss3.access-key", watch = true)
    private String AWSS3AccessKey;

    @ConfigValue(value = "awss3.secret-key", watch = true)
    private String AWSS3SecretKey;

    public String getAWSS3SecretKey() {
        return AWSS3SecretKey;
    }

    public void setAWSS3SecretKey(String amazonRekognitionSecretKey) {
        this.AWSS3SecretKey = amazonRekognitionSecretKey;
    }

    public String getAWSS3AccessKey() {
        return AWSS3AccessKey;
    }

    public void setAWSS3AccessKey(String amazonRekognitionAccessKey) {
        this.AWSS3AccessKey = amazonRekognitionAccessKey;
    }
}