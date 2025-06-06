@echo off
setlocal

REM === Configuration ===
set "PROJECT_DIR=C:\Users\swpawar\Downloads\community\community"
set "WAR_NAME=community.war"
set "TOMCAT_DIR=C:\Users\swpawar\Desktop\apache-tomcat-10.1.36"
set "DEPLOY_DIR=%TOMCAT_DIR%\webapps"
set "BACKUP_WAR_NAME=%WAR_NAME:.war=_bkp.war%"

echo ================================================================
echo         DEPLOYING SPRING BOOT WAR TO TOMCAT
echo ================================================================

:ASK_CREATE_WAR
echo.
set /p CREATE_WAR=" Do you want to create a new WAR file? (Y/N): "
if /I "%CREATE_WAR%"=="Y" (
    goto CREATE_WAR
) else if /I "%CREATE_WAR%"=="N" (
    goto CHECK_WAR_EXISTENCE
)
echo ❗ Please enter Y or N.
goto ASK_CREATE_WAR

:CREATE_WAR
echo.
echo ================================================================
echo         Stage 1: Building new WAR file using Maven...
echo ================================================================
pushd "%PROJECT_DIR%"
call mvn clean package
if errorlevel 1 (
    echo  Maven build failed! Stopping deployment.
    popd
    pause
    goto END
)

echo ================================================================
echo  Maven build successful....
echo ================================================================
popd

REM Proceed to deployment after build
goto CHECK_WAR_EXISTENCE

:CHECK_WAR_EXISTENCE
echo.
echo ================================================================
echo         Stage 2: Checking if WAR file exists...
echo ================================================================
if exist "%PROJECT_DIR%\target\%WAR_NAME%" (
    goto DEPLOY_WAR
) else (
    echo ❗ WAR file not found at: %PROJECT_DIR%\target\%WAR_NAME%
    echo Deployment stopped............
    pause
    goto END
)

:DEPLOY_WAR
echo.
echo ================================================================
echo         echo   Stage 3: Stopping Tomcat...
echo ================================================================

call "%TOMCAT_DIR%\bin\shutdown.bat"
timeout /t 5 >nul

echo.
echo ================================================================
echo   Stage 4: Backing up old WAR (overwriting if exists)...
echo ================================================================

if exist "%DEPLOY_DIR%\%WAR_NAME%" (
    if exist "%DEPLOY_DIR%\%BACKUP_WAR_NAME%" (
        del /f /q "%DEPLOY_DIR%\%BACKUP_WAR_NAME%"
    )
    ren "%DEPLOY_DIR%\%WAR_NAME%" "%BACKUP_WAR_NAME%"
) else (
    echo No existing WAR file to backup........
)

echo.
echo ================================================================
echo  Stage 5: Copying new WAR to Tomcat webapps...
echo ================================================================

copy /Y "%PROJECT_DIR%\target\%WAR_NAME%" "%DEPLOY_DIR%\"

echo.
echo ================================================================
echo   Stage 6: Starting Tomcat...
echo ================================================================
call "%TOMCAT_DIR%\bin\startup.bat"
timeout /t 5 >nul

echo.
echo ================================================================
echo  Stage 7: Opening browser to login page...
echo ================================================================
start http://localhost:8080/community/login

echo.
echo ================================================================
echo  Deployment completed successfully!
echo ================================================================
goto END

:END
pause
exit /b
