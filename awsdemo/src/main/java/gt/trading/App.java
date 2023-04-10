package gt.trading;

import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
import software.amazon.awssdk.auth.credentials.ProfileCredentialsProvider;
import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.CreateBucketRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.S3Object;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

import com.amazonaws.services.securitytoken.model.AssumeRoleRequest;

public class App {

    public static void main(String[] args) {
        String bucketname = "test-bucket-name";
        String filename = "test-file";
        String filepath = "/Users/test-file.jpg";
        
        S3Client client = S3Client.builder()
            .credentialsProvider(null)
            .region(Region.US_EAST_1)
            .build();
        
        PutObjectRequest request = PutObjectRequest.builder()
                .bucket(bucketname)
                .key(filename)
                .build();

        client.putObject(request, RequestBody.fromFile(new File(filepath)));

    }

}
