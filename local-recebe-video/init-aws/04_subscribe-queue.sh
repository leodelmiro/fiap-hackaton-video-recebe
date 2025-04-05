#!/bin/bash
awslocal sns subscribe --topic-arn "arn:aws:sns:us-east-1:000000000000:novo-video-recebido" --protocol sqs --notification-endpoint "arn:aws:sqs:us-east-1:000000000000:processa-video"
awslocal sns subscribe --topic-arn "arn:aws:sns:us-east-1:000000000000:novo-video-recebido" --protocol sqs --notification-endpoint "arn:aws:sqs:us-east-1:000000000000:video-em-processamento"