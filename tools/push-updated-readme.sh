#!/bin/bash
# Based on:
# https://gist.github.com/willprice/e07efd73fb7f13f917ea
# https://github.com/StoPlay/stoplay-ext/blob/fb0c721e69c48646f77cce98e2b5f6f83854cab2/.travis/push.sh
# https://gist.github.com/mitchellkrogza/a296ab5102d7e7142cc3599fca634203

REPO="truhlikfredy/osd-font-converter"


check_if_repo_changed() {
    local head_ref branch_ref

    head_ref=$(git rev-parse HEAD)
    if [[ $? -ne 0 || ! $head_ref ]]; then
        err "failed to get HEAD reference"
        exit 1
    fi

    branch_ref=$(git rev-parse "$TRAVIS_BRANCH")
    if [[ $? -ne 0 || ! $branch_ref ]]; then
        err "failed to get $TRAVIS_BRANCH reference"
        exit 1
    fi

    if [[ $head_ref != $branch_ref ]]; then
        msg "HEAD ref ($head_ref) does not match $TRAVIS_BRANCH ref ($branch_ref)"
        msg "someone may have pushed new commits before this build cloned the repo"
        exit 1
    fi
}


show_git_change() {
    echo "Show what was changed in the readme:"
    git diff --no-ext-diff README.md
    git status
}


setup_git() {
  echo "Changing git configuration to contain travis name and email"
  git config --global user.email "travis@travis-ci.org"
  git config --global user.name "Travis CI"
  git config --global push.default current
}


commit_readme() {
  # Make sure this commit will not trigger a build, as then it would trigger release and release would trigger this
  # readme change recursively. Use keyword to skip build on this commit.
  # https://docs.travis-ci.com/user/customizing-the-build/#skipping-a-build
  # https://github.com/travis-ci/travis-ci/issues/911

  echo "Committing changes in the readme"
  git stash # So the checkout will not be affected with local changes done with the build, stash them first
  git checkout ${TRAVIS_BRANCH}
  git stash pop
  git add README.md
  git commit --message "Updating README links (travis build: $TRAVIS_BUILD_NUMBER) [ci skip]" # the [ci skip] is important
  # the [skip <keyword>] doesn't work well, while [<keyword> skip] works every time:
}


push_updated_files() {
  echo "Setting the remote ${REPO} repository to contain GITHUB token (supplied as secret enviroment variable from travis)"
  # git remote add origin-travis https://${GITHUB_TOKEN}@github.com/${REPO}.git

  echo "Pushing the commit"
  # https://help.github.com/articles/which-remote-url-should-i-use/

  REMOTE="https://${GITHUB_TOKEN}@github.com/${REPO}.git"

  # https://docs.travis-ci.com/user/best-practices-security/
  # https://docs.travis-ci.com/user/environment-variables
  git push "${REMOTE}" "${TRAVIS_BRANCH}" >/dev/null 2>&1

  echo "Status..."
  git status
}


SCRIPT_PATH="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )" #get path of your script on matter what the current dir is
cd $SCRIPT_PATH/..


show_git_change
check_if_repo_changed
setup_git
commit_readme
push_updated_files
echo "Commit with updated readme should be in the repository"
