### **Selenium UI tests boilerplate code**

1. clone this repo and cd into into the root folder
2. install dependencies using `mvn clean install` 
3. Navigate into `qa-revision/src/test/resources` and execute following commands in different tabs of your system terminal.
   1. `java -jar selenium-server-standalone-3.141.59.jar`
   2. `./chromedriver`
   3.  In case you encounter error: "chromedriver" cannot be opened because the developer cannot be verified, then do following: `xattr -d com.apple.quarantine chromedirver`
4. Run python server `python3 -m http.server 8080` inside `qa-revision/src/test/resources/testWebsite`