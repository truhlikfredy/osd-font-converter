#!/bin/bash
# Based on: https://gist.github.com/willprice/e07efd73fb7f13f917ea

REPO="truhlikfredy/osd-font-converter"


setup_git() {
  echo "Changing git configuration to contain travis name and email"
  git config --global user.email "travis@travis-ci.org"
  git config --global user.name "Travis CI"
}


commit_readme() {
  # Make sure this commit will not trigger a build, as then it would trigger release and release would trigger this
  # readme change recursively. Use keyword to skip build on this commit.
  # https://docs.travis-ci.com/user/customizing-the-build/#skipping-a-build

  echo "Committing changes in the readme"
  git add README.md
  git commit --message "Updating README links (travis build: $TRAVIS_BUILD_NUMBER) [skip-ci]"
}


push_updated_files() {
  echo "Pushing the commit"
  git remote add origin-travis https://${GITHUB_TOKEN}@github.com/${REPO}.git > /dev/null 2>&1
  git push --quiet --set-upstream origin-travis master
}


SCRIPT_PATH="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )" #get path of your script on matter what the current dir is
cd $SCRIPT_PATH/..

setup_git
commit_readme
push_updated_files
