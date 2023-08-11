#!/bin/bash
aws s3api create-bucket \
--bucket com.ubds.swift.s.sai-ult-test-bucket-cli \
--create-bucket-configuration LocationConstraint=eu-west-2 \
--profile UBDS