@echo off
setlocal enabledelayedexpansion

:: while loop for script options
:getopts
if /i "%1" == "-u" set user=%~2 & shift
if /i "%1" == "-p" set PGPASSWORD=%~2 & shift
shift
if not "%1" == "" goto getopts

:: get pipe data
set "ins=insert into public.vets(id,lat,lon) values"

for /f delims^=^ eol^= %%r in ('findstr "^"') do call set "ins=%%ins%% (%%r),"
call set "ins=%%ins:~0,-1%%;"

:: trim script options
call :trim %user% user
call :trim %PGPASSWORD% PGPASSWORD

:: execute db scripts
psql -U %user% -w -f createdb.sql
psql -U %user% -w -d puppycare -f createtable.sql
psql -U %user% -w -d puppycare -c "%ins%"


:: functions
EXIT /B %ERRORLEVEL%

:trim
set "%~2=%~1"
exit /B 0
