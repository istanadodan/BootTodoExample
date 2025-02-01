param(
    [string]$dir = "",
    [string]$act = "d"
)

if (-not $dir) {
  # 이동
  $dir = "D:\works\react-project\docker\minikube"
}

# cd $dir

if ($act -eq "d") {
  # down
  $cmd = "kdf"
} else {
  # up
  $cmd = "kaf"
}
write-host "<<$dir>>"
write-host $cmd

# 실행 : & => 문자열을 실행가능 명령어로 실행
ls $dir/*.yaml | % {$_.name} | % { & $cmd $_}