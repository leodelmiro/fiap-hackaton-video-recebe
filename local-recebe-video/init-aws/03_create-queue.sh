#!/bin/bash
awslocal sqs create-queue --queue-name processa-video
awslocal sqs create-queue --queue-name video-em-processamento