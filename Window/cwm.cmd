cd /tools/DUKE/cwm/ramdisk
find . | cpio -o -H newc | gzip > ../ramdisk.cpio.gz
cd ..
./mkbootimg --base 0x00200000 --pagesize 2048 --kernel zImage --ramdisk ramdisk.cpio.gz -o recovery.img