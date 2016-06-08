@echo off
echo *****************************************************************
echo      my_modules_package_jar.bat
echo                     by kingrui.zhang
echo                           2016.03.29
echo  ˵����
echo     1. ��װapache-maven��
echo     2. ��ѹapache-maven��������ϵͳ����%MAVEN_HOME%Ϊ��ѹ�ĳ���Ŀ¼��
echo     3. ���PATH������%MAVEN_HOME%\bin�������JAVE_HOME�����Ƿ���ȷ��
echo     4. ������ʾ������mvn -v ��鰲װ�Ƿ�ɹ���
echo     5. �˽ű��������ǰĿ¼����Ŀ¼������pom.xml������jar����releaseĿ¼��
echo     6. ����compile_order.txt�ļ��е�������˳��
echo *****************************************************************

REM *******���ò�������*******
SET WORK_FOLDER=%~dp0
SET h=%time:~0,2%
SET hh=%h: =0%
SET OPNAME=%date:~0,4%%date:~5,2%%date:~8,2%_%hh%%time:~3,2%%time:~6,2%
SET LOGFILE=%WORK_FOLDER%my_modules_packing_log_%OPNAME%.txt

echo --checking compile_order.txt.
if not exist %WORKFOLDER%compile_order.txt dir /a:d /b %WORKFOLDER% >%WORKFOLDER%compile_order.txt

echo --install parent pom.xml
echo --mvn clean install
call mvn clean install >>%LOGFILE%

echo --start packing.
if not exist %WORK_FOLDER%release md %WORK_FOLDER%release
if exist %WORK_FOLDER%release del /f /s /q %WORK_FOLDER%release\*.* >>%LOGFILE%

for /f %%m in (%WORKFOLDER%compile_order.txt) DO (
if exist %WORK_FOLDER%%%m\pom.xml (
echo --packing %%m.
echo --packing %%m. >>%LOGFILE%
cd /d %WORK_FOLDER%%%m
call mvn clean compile -Dmaven.test.skip=true package >>%LOGFILE%
if exist %WORK_FOLDER%%%m\target\%%m*.jar (
copy /y %WORK_FOLDER%%%m\target\%%m*.jar %WORK_FOLDER%release >>%LOGFILE%
echo --pack %%m completed. >>%LOGFILE%
echo ******************************************************** >>%LOGFILE%
echo --pack %%m completed.
) else (
if exist %WORK_FOLDER%%%m\target\%%m*.war (
copy /y %WORK_FOLDER%%%m\target\%%m*.war %WORK_FOLDER%release >>%LOGFILE%
echo --pack %%m completed. >>%LOGFILE%
echo ******************************************************** >>%LOGFILE%
echo --pack %%m completed.
)else (
echo --pack %%m faild. >>%LOGFILE%
echo ******************************************************** >>%LOGFILE%
echo --pack %%m faild.
)
) 
)else (
cd /d %WORK_FOLDER%
)
)
cd /d %WORK_FOLDER%

echo --rename packages.
for /f "tokens=1,2,* delims=- " %%a in ('dir /b %WORK_FOLDER%release\') do (
if exist "%WORK_FOLDER%release\*-.jar" ren "%WORK_FOLDER%release\%%a-%%b-%%c" "%%a.%%b.jar" >>%LOGFILE%
)
echo --see [%LOGFILE%] for details.
echo --done.