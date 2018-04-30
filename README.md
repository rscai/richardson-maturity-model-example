[![Build Status](https://travis-ci.org/rscai/richardson-maturity-model-example.svg?branch=master)](https://travis-ci.org/rscai/richardson-maturity-model-example)
![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=io.github.rscai%3Arichardson-maturity-model-example&metric=alert_status)
![Lines of Code](https://sonarcloud.io/api/project_badges/measure?project=io.github.rscai%3Arichardson-maturity-model-example&metric=ncloc)
![Coverage](https://sonarcloud.io/api/project_badges/measure?project=io.github.rscai%3Arichardson-maturity-model-example&metric=coverage)
# Ricahrdson Maturity Model Example

## Deploy

```shell
heroku plugins:install heroku-cli-deploy

heroku create rest-example-rscai --no-remote
heroku deploy:jar build/libs/richardson-maturity-model-example-0.0.1-SNAPSHOT.jar  --app rest-example-rscai
```
