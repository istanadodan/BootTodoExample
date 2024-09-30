minikube delete

minikube start `
--memory=8192 `
--cpus=4 `
--static-ip 192.168.49.2 `
--docker-opt 'group=1001'
# --mount-string= "/var/run/docker.sock:/var/run/docker.sock" `
# --mount `
# --host-only-cidr=192.168.55.1/24 `
# --network minikube `

# docker image
# minikube image load docker-server
minikube image load docker-mysql
minikube image load react
minikube image load docker-jenkins

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

cd $manifestDirectory[0]
# db 설정
.\db_secrets.bat
.\registry_auth.bat
# mnt 설정
.\bg_jobs.ps1

Write-Host "All manifest files from both directories applied successfully."