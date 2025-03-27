#!/bin/bash
awslocal s3api create-bucket --bucket hackaton-tech-challenge-videos-ld

awslocal s3api put-public-access-block \
    --bucket hackaton-tech-challenge-videos-ld \
    --public-access-block-configuration BlockPublicAcls=false,IgnorePublicAcls=false,BlockPublicPolicy=false,RestrictPublicBuckets=false