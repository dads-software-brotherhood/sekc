#!/bin/bash

printf "\n" >> /home/vagrant/.bashrc
echo 'export PS1="\[\e[01;34m\]sekc\[\e[0m\]\[\e[01;37m\]:\w\[\e[0m\]\[\e[00;37m\]\n\\$ \[\e[0m\]"' >> /home/vagrant/.bashrc
printf "\n" >> /home/vagrant/.bashrc

sudo yum clean all
sudo yum -y update

#Downloading configuration files for java, maven, and mongodb

curl https://gist.githubusercontent.com/danimaniarqsoft/177b6c8cb579f0cac87b8d13d74e886c/raw/619c9e672496cddab49e92f44765a295b488ffb0/maven.sh > maven.sh
curl https://gist.githubusercontent.com/danimaniarqsoft/177b6c8cb579f0cac87b8d13d74e886c/raw/619c9e672496cddab49e92f44765a295b488ffb0/java.sh > java.sh


sudo mv maven.sh /etc/profile.d/
sudo mv java.sh /etc/profile.d/


## Install Java 8
curl -L -b "oraclelicense=a" http://download.oracle.com/otn-pub/java/jdk/8u131-b11/d54c1d3a095b4ff2b6607d096fa80163/jdk-8u131-linux-x64.rpm -O
sudo yum -y localinstall jdk-8u131-linux-x64.rpm

### Crear liga java.csh
sudo ln -s /etc/profile.d/java.sh /etc/profile.d/java.csh

## Install Maven
curl http://www-us.apache.org/dist/maven/maven-3/3.3.9/binaries/apache-maven-3.3.9-bin.tar.gz > apache-maven-3.3.9-bin.tar.gz
sudo mv apache-maven-3.3.9-bin.tar.gz /opt
sudo tar xzf /opt/apache-maven-3.3.9-bin.tar.gz -C /opt
sudo ln -s /opt/apache-maven-3.3.9 /opt/maven

source /etc/profile.d/maven.sh
source /etc/profile.d/java.sh
source /etc/profile.d/java.csh

## Install Git
sudo yum -y install git


## Install Tomcat

sudo groupadd tomcat
sudo useradd -M -s /bin/nologin -g tomcat -d /opt/tomcat tomcat
curl https://archive.apache.org/dist/tomcat/tomcat-8/v8.5.12/bin/apache-tomcat-8.5.12.tar.gz > apache-tomcat-8.5.12.tar.gz
sudo mkdir /opt/tomcat
sudo tar xvf apache-tomcat-8*tar.gz -C /opt/tomcat --strip-components=1
sudo curl -o /opt/tomcat/webapps/manager/META-INF/context.xml https://gist.githubusercontent.com/danimaniarqsoft/558f361ccc5119cc4b47481b2b17b8b4/raw/167cfbe58a27f1de17d14fd7e11bd1cfc4ebeacc/context.xml
sudo curl -o /opt/tomcat/webapps/host-manager/META-INF/context.xml https://gist.githubusercontent.com/danimaniarqsoft/558f361ccc5119cc4b47481b2b17b8b4/raw/167cfbe58a27f1de17d14fd7e11bd1cfc4ebeacc/context.xml
sudo curl -o /opt/tomcat/conf/tomcat-users.xml https://gist.githubusercontent.com/danimaniarqsoft/558f361ccc5119cc4b47481b2b17b8b4/raw/bf4e5a62c9a39f583ddee0b0abea42e320bd5701/tomcat-users.xml
sudo chgrp -R tomcat /opt/tomcat
sudo chmod -R g+r /opt/tomcat/conf
sudo chmod g+x /opt/tomcat/conf
sudo chown -R tomcat /opt/tomcat/webapps/ /opt/tomcat/work/ /opt/tomcat/temp/ /opt/tomcat/logs/
sudo curl -o /etc/systemd/system/tomcat.service https://gist.githubusercontent.com/danimaniarqsoft/177b6c8cb579f0cac87b8d13d74e886c/raw/82b06145e723191038a63ab0413cc791b83914b9/tomcat.service
sudo systemctl daemon-reload
sudo systemctl start tomcat
sudo systemctl enable tomcat

# Access to http://[domain]:8080
# ej. http://localhost:8080
# ej. http://2007.249.80.34:8080

# Install Apache Server

sudo yum -y install httpd
sudo systemctl start httpd.service
sudo systemctl enable httpd.service



# Tools

## htop

sudo yum -y install epel-release
sudo yum -y install htop

## 
