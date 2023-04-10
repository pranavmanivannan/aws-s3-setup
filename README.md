# AWS S3 Guide
My own AWS S3 guide that includes how to setup and upload to buckets using Java.

Based on ga-wdi-boston's guide


## Instructions

Download the .zip file above.

Follow the steps outlined to create and gain access to an AWS S3 bucket.

## What's Needed

-   An `AWS` _(Amazon Web Services)_ account (If you do not have an account, open [AWS](https://aws.amazon.com/) and click
 `Sign In to the Console`. There is a free tier available.)
-   A Credit card is required to verify your AWS account.

## AWS S3 access control

1.  Open the [AWS Consle](https://console.aws.amazon.com/console/) in your
 browser
1.  From the `AWS` console open tabs for
 `IAM` _(Identity and Access Management)_ and `S3` _(Simple Storage Service)_.

### Identity and Access Management (IAM)

Identities are how we grant access to AWS APIs.

In the [IAM](https://console.aws.amazon.com/iam) tab:


1.  Select `Users` in the left sidebar.
1.  Click `Add User` near the top of the page.
1.  Enter `your-name` into the text box.
1.  Click Next
1.  Highlight Add User to Group
1.  If you do not already have a group, do the following:

    1. Click `Create group`
    1. Enter `group-name`
    1. Search policies for AmazonS3FullAccess
    1. Select its checkbox and press `Create user group`

1.  Click Next
1.  Click create User
_Then_
1.  Click on your newly created user.
1.  Click on the security credentials tab.
1.  Click the small red `x` to the right of your existing access key to delete it.
1.  Click `Create access key`
1.  When complete, click `Download .csv file` and save the CSV to this repository. (This is
the only time you'll be able to see your access key, but you can generate a new one anytime
and are encouraged to rotate them frequently)
1.  Save the file `credentials.csv` to this repository.
1.  Copy the Access Key ID and Secret Access Key and save it in the ```creds.txt``` file.
1.  Click `Close`
1.  Click on the newly created user.
1.  Copy the `User ARN` _(Amazon Resource Name)_ at the top of the page and save it in the ```creds.txt``` file.

### Simple Storage Service (S3)

S3 stores files you upload in `buckets`.  A bucket is a top-level namespace
 for your files.

In the [S3](https://console.aws.amazon.com/s3) tab:

1.  Click `Create Bucket`.
 This opens the `Create a Bucket - Select a Bucket Name and Region` modal.
1.  Enter a name in the `Bucket Name` box. It must be unique among all S3
 buckets and in all lowercase characters.
1.  Select `US Standard` for the `Region`.
1.  Click `Create`.
1.  Highlight your bucket and select the `Properties` tab on the right side.
1.  Open the `Permissions` dropdown in the right sidebar.
1.  Click `Add bucket policy` near the bottom of the `Permissions` dropdown.
1.  There are two ways to add bucket policies:

1. Version 1:
    1. On the right side of the text editor, there will be a text box that
      says `Filer services`.
    1. Search for S3 and click on it
    1. Now in the 'Filter actions' text box, search for PutObject and PutObjectAcl
    1. Add both of them

1. Version 2:
    1.  At the bottom of the `Bucket Policy Editor` modal,
     click `AWS Policy Generator`.  This opens the AWS Policy Generator page.
    1.  On the AWS Policy Generator page

        1.  Step 1: Select Policy Type

            1.  For `Select Type of Policy` use `S3 Bucket Policy`.

        1.  Step 2: Add Statement(s)

            1.  Select `Allow` for `Effect`.
            1.  Paste the User ARN into the `Principal` box.
            1.  Select `PutObject` and `PutObjectAcl` for `Actions`.
            1.  Enter `arn:aws:s3:::<bucket_name>/*` into the
              `Amazon Resource Name (ARN)` box.
            1.  Click the `Add Statement`.

        1.  Step 3: Generate Policy

            1.  Click `Generate Policy`
            1.  Copy the JSON from the `Policy JSON Document` modal.

    1.  Return to the S3 tab.
    1.  Paste the bucket policy into the `Bucket Policy Editor` modal.
    
    
1.  Click `Save`.
1.  Click `Save` in the `Permissions` dropdown.

You have now created and granted access to an S3 bucket for your IAM user to upload.

These steps allow restricted upload access to one bucket for the identity `your-name`.


#### Example bucket policy JSON (taken from ga-wdi-boston)

```json
{
  "Version": "2012-10-17",
  "Id": "Policy1439826519004",
  "Statement": [
    {
      "Sid": "Stmt1439826516658",
      "Effect": "Allow",
      "Principal": {
        "AWS": "arn:aws:iam::<AWS Account Id>:user/<IAM User Name>"
      },
      "Action": [
        "s3:PutObjectAcl",
        "s3:PutObject"
      ],
      "Resource": "arn:aws:s3:::<bucket_name>/*"
    }
  ]
}
```

### Environment Setup

Before you can use code to upload files on your computer to S3, you have to configure
a method for the AWS SDK to read your credentials (api access key and secret key). 
My preferred method is through environment variables, but you can see the full list 
of configuration options here: [AWS S3 Configuration Options](https://docs.aws.amazon.com/cli/latest/userguide/cli-chap-configure.html)

1. On macOS or Linux:

    1.  Open Terminal.
    1.  Copy paste the following code, line by line while replacing the keys and regions
        with ones that are in the .txt file.
    1.  ```export AWS_ACCESS_KEY_ID=AKIAIOSFODNN7EXAMPLE
           export AWS_SECRET_ACCESS_KEY=wJalrXUtnFEMI/K7MDENG/bPxRfiCYEXAMPLEKEY
           export AWS_DEFAULT_REGION=us-west-2```
    1.  Once done, you can close Terminal.

1. On Windows:

    1.  I recommend setting up the variables directly as Environment Variables.
    1.  Another option is to copy paste the following code into Command Prompt, line by line 
        while replacing the keys and regions with the ones in the .txt file.
    1.  ```setx AWS_ACCESS_KEY_ID AKIAIOSFODNN7EXAMPLE
           setx AWS_SECRET_ACCESS_KEY wJalrXUtnFEMI/K7MDENG/bPxRfiCYEXAMPLEKEY
           setx AWS_DEFAULT_REGION us-west-2```
    1.  Once done, you can close Command Prompt.

### VSCode Setup

To be able to use the AWS SDK, download the AWS Toolkit extension first.

Replace the bucket name, file name, and file path in App.java.

Run the file, and if there are no errors, you should see the file in your bucket after refreshing.
