Roadmap
=======

### v1.5 ###
* avoid unneeded *.jar files generation
* add application icon
* update README
    * add GUI screen shots
    * add weight [fit logo] (http://www.thisisant.com/consumer/ant-101/activity-icons) to README

### v2.0 ###
* TODOs processing
* create Windows, Linux, MacOS executable files
    * remove 'swt' from artifactId in local repo
    * use artifactId from local repo as platformId for file name generation
    * add MacOS SWT into local repo
* create web-site ???

### v2.5 ###
* review analogs
* add menu
    * File/Exit
    * Edit/Preferences
        * add fields set config support
        * add info in lbs and kgs
    * Help/About
        * link to FIT params explanation 
* add localization support
* update FIT SDK
    
### v??? ###
* plugins support
    * add Garmin Connect net uploading
    * add not Tanita scale input formats support ???
    * create **CSVParamsSupplier** (or extend **CmdLineParamsSupplier**) for batch uploading old Fit data
* change domain model to support multiple weight records
    * add domain validation
    * make domain model more strict (use reflection for model fields access)
* https://travis-ci.org usage ??? 
* Android port ???
* ProGuard usage ???

