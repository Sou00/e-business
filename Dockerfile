FROM ubuntu:22.04

ENV TZ=Europe/Warsaw

RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone

RUN apt-get update 
RUN apt update
RUN apt-get -qq -y install curl wget unzip zip

RUN apt-get install -y software-properties-common
RUN add-apt-repository -y ppa:deadsnakes/ppa
RUN apt-get install -y python3.8

RUN apt-get install -y openjdk-8-jdk
RUN useradd -ms /bin/bash sou
RUN adduser sou sudo

VOLUME home/sou/ebiznes
USER sou

WORKDIR /home/sou/
RUN mkdir ebiznes
RUN curl -s "https://get.sdkman.io" | bash

RUN bash -c "source $HOME/.sdkman/bin/sdkman-init.sh && sdk install kotlin && sdk install gradle "


CMD ["/bin/bash"]