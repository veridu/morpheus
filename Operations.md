Operations manual
=================

# Configuration

You need to set the `SPRING_APPLICATION_JSON` environment variable with the JSON configuration, such as in the following example:

```bash
export SPRING_APPLICATION_JSON='{"server":{"address":"127.0.0.1", "port": 8080}, "logging":{"file":"morpheus-spring.log"}, "morpheus":{"http":{"user":"cassio", "password":"god"}, "handlerPrivateKey": "XXXX", "handlerPublicKey": "XXXX", "useSSL": true, "IDOS_DEBUG": 1, "IDOS_API_URL": "https://api.idos.io/1.0"}, "aws": {"ACCESS_KEY": "XXXX", "SECRET": "XXXX"}}'
```

* The `server` variable controls the `address` and `port` where the service will run.
* The `logging` variable indicates the path of the logging file.
* The `morpheus` variable contains:
    * `http`: basic AUTH credentials;
    * `handlerPrivateKey`: which should be registered with `idOS`;
    * `handlerPublicKey`: which has the public key registered with `idOS`;
    * `useSSL`: indicates whether SSL checking happens when calling the `idOS` API;
    * `IDOS_DEBUG`: indicates whether the requests to `idOS` should be printed to stdout;
    * `IDOS_API_URL`: indicates the URL to connect to the `idOS` API.
* The `aws` variable contains:
    * `ACCESS_KEY`: access key for using the Amazon Rekognition service.
    * `SECRET`: secret for using the Amazon Rekognition service.

# Setup of Google Cloud Vision API 
The OCR task in Morpheus requires the use of Google Cloud Vision API.
A project has to be created in the [Google Cloud console](https://console.cloud.google.com). 
Credentials have to be created as well, so Morpheus can access the service.
The recommended way to do that is by creating a [Google Application Default Credentials](A detailed guide on creating the credentials is available [here](https://cloud.google.com/docs/authentication#getting_credentials_for_server-centric_flow).
The environment variable `GOOGLE_APPLICATION_CREDENTIALS` should be used to point to the file that defines the credentials.

In summary, the steps are as follows to obtain and configure credentials:
1. Go to the [API Console Credentials page](https://console.developers.google.com/project/_/apis/credentials).
2. From the project drop-down, select your project.
3. On the Credentials page, select the Create credentials drop-down, then select Service account key.
4. From the Service account drop-down, select an existing service account or create a new one. **Set the role as Service Account Users**.
5. For Key type, select the JSON key option, then select Create. The file automatically downloads to your computer.
6. Put the *.json file you just downloaded in a directory of your choosing. This directory must be private (you can't let anyone get access to this), but accessible to your web server code.
7. Set the environment variable GOOGLE_APPLICATION_CREDENTIALS to the path of the JSON file downloaded.
                                                                                    
You will also need to enable Google Cloud Vision on project settings in the [Google cloud console](https://console.cloud.google.com).

# Setup of Amazon Rekognition API
The Photo task in Morpheus requires Amazon Rekognition to perform face detection.

An IAM User credential needs to be created with permission to access the Rekognition service.
A guide on setting up that account is available [here](https://docs.aws.amazon.com/rekognition/latest/dg/setting-up.html#setting-up-iam).

Two variables have to be set in morpheus with the credentials: the access key and the secret.
Refer to the configuration section for these settings.

# Running the project

* Set the `SPRING_APPLICATION_JSON` environment variable, as explained in the [Configuration](#configuration) section.
* Move into the `target` folder and run the command:
* `java -jar morpheus-0.1.jar`