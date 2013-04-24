REM Param $1 = IP
REM Param $2 = UserName
REM Param $3 = Password

plink -ssh $1 -l $2 -pw $2 -m cwm.cmd
adb wait-for-device root
del recovery.img
wget --user=$2 --password=$3 ftp://$1/../../../tools/DUKE/cwm/recovery.img
adb wait-for-device push recovery.img /cache
adb shell dd if=/cache/recovery.img of=/dev/block/mmcblk0p16
adb reboot recovery