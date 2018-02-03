package io.aws.s3.utilility;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.Bucket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tapan N. Banker on 2/3/18.
 * @Author Tapan N.  Banker
 * The Class Provide utility to Access AWS S3 Service
 */
public class AWSS3Utility {

    private static Logger LOGGER = (Logger) LoggerFactory.getLogger(AWSS3Utility.class);

    /**
     * This method performs Secure Login to AWS encrypted AES 256 bit algorithm
     * @param accessKey accessKey
     * @param secretKey secretKey
     * @return BasicAWSCredentials
     * @throws Exception
     */
    public static BasicAWSCredentials loginToAws(String accessKey, String secretKey) throws Exception {
        String plainAccessKey = EncryptionAES.decrypt(accessKey);
        String plainSecretKey = EncryptionAES.decrypt(secretKey);
        BasicAWSCredentials credentials = new BasicAWSCredentials(plainAccessKey, plainSecretKey);
        LOGGER.warn("ALERT Successful Login attempted by accessKey ******" + accessKey + "****** at "+ Instant.now() );
        return credentials;
    }


    /**
     * List all the S3 Buckets in the AWS Account
     * @param credentials BasicAWSCredentials
     */
    public static List<String> listS3Buckets(BasicAWSCredentials credentials) {

        // Get S3 client
        AmazonS3 s3Client = AmazonS3ClientBuilder.standard().withCredentials(new AWSStaticCredentialsProvider(credentials)).build();
        // Store the list of Buckets in ArrayList
        ArrayList<String> listOfS3Buckets = new ArrayList<>();

        // Iterate each bucket
        for (Bucket bucket : s3Client.listBuckets()) {
            listOfS3Buckets.add(bucket.getName());
        }
        LOGGER.info(" Returning the List of S3 Buckets");
        return listOfS3Buckets;
    }

}
