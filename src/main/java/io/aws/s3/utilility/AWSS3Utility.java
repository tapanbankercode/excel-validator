package io.aws.s3.utilility;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.Bucket;
import io.excel.validation.Book.BookExcelReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Tapan N. Banker on 2/3/18.
 * @Author Tapan N.  Banker
 * The Class Provide utility to Access AWS S3 Service
 */
public class AWSS3Utility {

    private static Logger LOGGER = (Logger) LoggerFactory.getLogger(BookExcelReader.class);

    /**
     * This method performs Secure Login to AWS encrypted AES 256 bit algorithm
     * @param accessKey
     * @param secretKey
     * @return BasicAWSCredentials
     * @throws Exception
     */
    public static BasicAWSCredentials loginToAws(String accessKey, String secretKey) throws Exception {
        String plainAccessKey = EncryptionAES.decrypt(accessKey);
        String plainSecretKey = EncryptionAES.decrypt(secretKey);
        BasicAWSCredentials credentials = new BasicAWSCredentials(plainAccessKey, plainSecretKey);
        LOGGER.info(" Successful Login attempted by accessKey "+plainAccessKey);
        return credentials;
    }


    /**
     * List all the S3 Buckets in the AWS Account
     * @param credentials BasicAWSCredentials
     */
    public static void listS3Buckets(BasicAWSCredentials credentials) {
        AmazonS3 s3Client = AmazonS3ClientBuilder.standard().withCredentials(new AWSStaticCredentialsProvider(credentials)).build();
        for (Bucket bucket : s3Client.listBuckets()) {
            System.out.println(" - " + bucket.getName());
        }
    }

}
