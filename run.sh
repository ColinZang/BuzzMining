#!/bin/bash

[ "$#" -lt 2 ] && echo 'usage: ./run.sh <keyword> <website>' && exit 0

EXECUTABLE='hadoop Sentiment'
KEYWORD=$1
WEBSITE=$2
DELETEHADOOP='hdfs dfs -rm -r .staging'
DELETELOCAL='rm -rf'
EXPORT='export HADOOP_CLASSPATH=Sentiment.jar'
COPYTOLOCAL='hdfs dfs -copyToLocal'
OUTFILE=${KEYWORD}_${WEBSITE}
COMPILEJAVA='javac PostProcess.java'
RUNJAVA='java PostProcess'

${DELETEHADOOP} ${OUTFILE}
${DELETELOCAL} ${OUTFILE}
${EXPORT}
${EXECUTABLE} ${KEYWORD} ${WEBSITE} 
${COPYTOLOCAL} ${OUTFILE}
${DELETEHADOOP} ${OUTFILE}
${COMPILEJAVA}
${RUNJAVA} ${OUTFILE} ${WEBSITE}

