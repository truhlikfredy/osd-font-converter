#!/bin/bash

get_latest_tag() {
    # https://gist.github.com/lukechilds/a83e1d7127b78fef38c2914c4ececc3c

    # do not relly on the git tag command to work as your current local repository might not be upto date
    # and do not fetch tags as the build enviroment at that stage (without changing remotes to contain tokens)
    # might not have access/credentials to do git fetch. pre-build might have access to the repository, but not
    # build itself, so use simple API to fetch this information

    curl --silent "https://api.github.com/repos/$1/releases/latest" | jq -r .tag_name
}

SCRIPT_PATH="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )" #get path of your script on matter what the current dir is
FILE="$SCRIPT_PATH/../README.md" # where the readme is located relatively to this script
GITHUB_REPO="truhlikfredy/osd-font-converter" # github repository in question
GITHUB_PATH_EXPRESSION="https://github\.com/$GITHUB_REPO/releases/download"
LATEST_TAG=`get_latest_tag truhlikfredy/osd-font-converter` # getting the latest release/tag

echo "Updating $FILE of $GITHUB_REPO repository to contain links to newest tag $LATEST_TAG"
sed -i -e "s%$GITHUB_PATH_EXPRESSION/.*/%$GITHUB_PATH_EXPRESSION/$LATEST_TAG/%" $FILE
