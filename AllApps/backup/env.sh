JAVA_HOME=/home/prakash/apps/jdk1.8.0_111
export JAVA_HOME
CATALINA_HOME=/home/prakash/apps/apache-tomcat-7.0.73
export CATALINA_HOME
PATH=$CATALINA_HOME/bin:$PATH
ANT_HOME=/home/prakash/apps/apache-ant-1.9.7
export ANT_HOME
PATH=$JAVA_HOME/bin:$ANT_HOME/bin:$PATH
M2_HOME=/home/prakash/apps/apache-maven-3.3.9
export M2_HOME
export M2=$M2_HOME/bin
PATH=$M2:$PATH
export PATH