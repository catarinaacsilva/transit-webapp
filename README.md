# Real time information about transit


## Create an account to get Bing Api Key

1. Open your browser and go to `https://www.bingmapsportal.com/`
2. Create an account
3. Go to `My account` and click on `My keys`
4. Fill all the fields with application information
5. Get key

## Integrate use of this key on project

1. Create the file `config.cfg` on `src/main/java/ua.es.transit.resources`
2. Edit this file with `key = XXXXX`. XXXX is the key previous generated.

## Some notes about gitignore

1. All files named Test.java should be used just test. Files with this name doesn't go to git
2. Api key should be write on config file and this file should be present on gitignore.

