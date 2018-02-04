package io.aws.s3.utilility;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.*;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tapan N. Banker
 *
 * @Author Tapan N.  Banker
 * The Class Provide utility to Access AWS S3 Service
 */
public class AWSS3Utility {

    private static Logger LOGGER = (Logger) LoggerFactory.getLogger(AWSS3Utility.class);

    /**
     * This method performs Secure Login to AWS encrypted AES 256 bit algorithm
     *
     * @param accessKey accessKey
     * @param secretKey secretKey
     * @return BasicAWSCredentials
     * @throws Exception
     */
    public static BasicAWSCredentials loginToAws(String accessKey, String secretKey) throws Exception {
        String plainAccessKey = EncryptionAES.decrypt(accessKey);
        String plainSecretKey = EncryptionAES.decrypt(secretKey);
        BasicAWSCredentials credentials = new BasicAWSCredentials(plainAccessKey, plainSecretKey);
        LOGGER.warn("ALERT Successful Login attempted by accessKey ******" + accessKey + "****** at " + Instant.now());
        return credentials;
    }


    /**
     * This method will download the Object from the S3 Bucket
     *
     * @param credentials            AWSCredentials
     * @param bucketName             bucket name where the file to be downloaded from
     * @param objectPath             file path in the S3 bucket
     * @param localDirectoryLocation location of the directory the file to be downloaded
     * @throws IOException
     */
    public static void downloadTheObject(BasicAWSCredentials credentials, String bucketName, String objectPath, String localDirectoryLocation) throws IOException {
        // Get S3 client
        AmazonS3 s3Client = AmazonS3ClientBuilder.standard().withCredentials(new AWSStaticCredentialsProvider(credentials)).build();
        // Get the S3 Object
        S3Object s3object = s3Client.getObject(bucketName, objectPath);
        // Stream the object on the Local inputstream
        S3ObjectInputStream inputStream = s3object.getObjectContent();
        // Download the File from S3 to Local Drive
        FileUtils.copyInputStreamToFile(inputStream, new File(localDirectoryLocation));
    }


    /**
     * This method will upload the object from Local machine into S3 Bucket
     *
     * @param credentials     credentials AWS
     * @param bucketName      bucketName
     * @param objectPath      objectPath on the S3 Bucket Inside the Bucket aka Key
     * @param uploadeFilePath upload FilePath - File path on the local Machine
     * @throws IOException
     */
    public static void uploadObjectToS3Bucket(BasicAWSCredentials credentials, String bucketName, String objectPath, String uploadeFilePath) throws IOException {
        // Get S3 client
        AmazonS3 s3Client = AmazonS3ClientBuilder.standard().withCredentials(new AWSStaticCredentialsProvider(credentials)).build();
        // Create the File object to be uploaded
        File file = new File(uploadeFilePath);
        // Upload the File to S3
        PutObjectResult putObjectResult = s3Client.putObject(new PutObjectRequest(bucketName, objectPath, file));
        if (null == putObjectResult.getContentMd5() ) {
            LOGGER.error(" Failure in  uploaded to S3 Bucket " + putObjectResult.getVersionId() + " from location " + uploadeFilePath);
        } else {
            LOGGER.info(" Object Uploaded successfully to S3 Bucket " + bucketName + " Object Md5 " + putObjectResult.getContentMd5() + " from location " + uploadeFilePath);
        }
    }


    /**
     * This method will upload the object from Local machine into S3 Bucket. The Method will Also DELETE the file from the local machine.
     *
     * @param credentials     credentials AWS
     * @param bucketName      bucketName
     * @param objectPath      objectPath on the S3 Bucket Inside the Bucket aka Key
     * @param uploadeFilePath uploadeFilePath - File path on the local Machine
     * @throws IOException
     */
    public static void uploadObjectToS3BucketAndDelete(BasicAWSCredentials credentials, String bucketName, String objectPath, String uploadeFilePath) throws IOException {
        uploadObjectToS3Bucket(credentials, bucketName, objectPath, uploadeFilePath);
        deleteFileLocally(uploadeFilePath);
    }

    /**
     * This method will list the object in the S3 Bucket
     *
     * @param credentials
     * @param bucketName
     */
    public static List<String> listObjectsInBucket(BasicAWSCredentials credentials, String bucketName) {
        // Get S3 client
        AmazonS3 s3Client = AmazonS3ClientBuilder.standard().withCredentials(new AWSStaticCredentialsProvider(credentials)).build();
        // List of Object in the S3 Bucket
        ArrayList<String> objectListInS3Bucket = new ArrayList<>();
        ObjectListing objectListing = s3Client.listObjects(bucketName);
        for (S3ObjectSummary os : objectListing.getObjectSummaries()) {
            objectListInS3Bucket.add(os.getKey());
        }
        LOGGER.info("Returning the Object List in S3 Buckets");
        return objectListInS3Bucket;
    }


    /**
     * List all the S3 Buckets in the AWS Account
     *
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


    /**
     * This method will delete the File from the local directory
     *
     * @param filePath String Full Path to the file on local machine
     * @return
     */
    public static boolean deleteFileLocally(String filePath) {
        // File Object
        File removeS3MeasureFile = new File(filePath);
        // Delete the Measure Excel File
        if (removeS3MeasureFile.delete()) {
            LOGGER.info("File deleted at location " + filePath);
            return true;
        } else {
            LOGGER.warn("Failure in deleting file at " + filePath);
            return false;
        }
    }

}
