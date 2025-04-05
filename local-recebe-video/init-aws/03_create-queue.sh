#!/bin/bash
awslocal sqs create-queue --queue-name processa-video --attributes VisibilityTimeout=300
awslocal sqs create-queue --queue-name video-em-processamento
awslocal sqs create-queue --queue-name processamento-realizado
awslocal sqs create-queue --queue-name erro-processamento
awslocal sqs create-queue --queue-name notifica-video-processado
awslocal sqs create-queue --queue-name notifica-video-erro