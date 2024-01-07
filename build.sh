#!/bin/bash

#@Author - Hafner Peter
#Date: 2023/11/22


src=./src/main/java
bin=./target/classes
lib=../lib
doc=../doc

mvn compile &> /dev/null

if [ $? == 0 ]; then
  echo -e "\e[1;32m ***OK*** \e[0m The build was successful."
else
  echo -e "\e[1;31m ***ERR*** \e[0m The build was not successful"
fi

########################################################################################################################
# Building the .jar file!
########################################################################################################################

jarFile="${lib}/compiler.jar"

jar -cvf $jarFile -C $bin . &> /dev/null

if [ $? == 0 ]; then
  echo -e "\e[1;32m ***OK*** \e[0m jar file build successfully!"
else
  echo -e "\e[1;31m ***ERR*** \e[0m jar file NOT build successfully"
fi

########################################################################################################################
# Building the Documentation!
########################################################################################################################

# Documentation Path -->
docmodelpath="${doc}/java-find"


java -jar ~/bin/plantuml/plantuml.jar $src/dev/hafnerp/Main.java
# Generating the documentation
javadoc -d $docmodelpath -subpackages dev.hafnerp --source-path $src --class-path $bin:$lib &> /dev/null

if [ $? == 0 ]; then
  echo -e "\e[1;32m ***OK*** \e[0m documentation generated successfully in '$docmodelpath'!"
else
  echo -e "\e[1;31m ***ERR*** \e[0m documentation generated NOT successfully in '$docmodelpath'!"
fi