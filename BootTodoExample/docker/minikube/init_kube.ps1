# 기존 클러스터 삭제
minikube delete

# 클러스터 생성
# minikube start --memory 8192 --cpus 4 --static-ip 192.168.49.2 --mount-string "//var/run/docker.sock:/var/run/docker.sock" --mount
# --mount-string "//var/run/docker.sock:/var/run/docker.sock" `
# --mount: 마운트 기능을 활성화합니다
minikube start `
    --memory 8192 `
    --cpus 4 `
    --static-ip 192.168.49.2 `
    --mount-string="//./pipe/docker_engine://./pipe/docker_engine" `
    --mount

# docker image
minikube image load docker-server
minikube image load docker-mysql
minikube image load react

$manifestDirectory = @(
  "D:\works\react-project\reactexample\docker\minikube"
)

ForEach ($directory in $manifestDirectory) {

  Get-ChildItem -Path $directory -Filter *.yaml | ForEach-Object {
      $filePath = $_.FullName
      Write-Host "Applying manifest file: $filePath"
      
      # kubectl을 통해 파일 적용
      kubectl apply -f $filePath
  }
}

# db 설정
CD $manifestDirectory[0]
.\db_secrets.bat
.\registry_auth.bat
.\bg_jobs.ps1

Write-Host "All manifest files from both directories applied successfully."