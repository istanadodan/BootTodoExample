minikube delete --all
rm -r -force  "$env:HOMEPATH\.minikube"

minikube start `
--memory=8192 `
--cpus=4 `
--driver docker `
--insecure-registry "local-registry:5000" `
--mount-string="D:\.data\minikube:/mnt/host" `
--mount
#--docker-opt  "group=1000" `
# --v=7
# --static-ip 192.168.49.2 `
# --host-only-cidr=192.168.55.1/24 `
# --network minikube `

#istio 설치
#istioctl install --set profile=demo

#kubectl label ns default istio-injection=enabled 

# docker image
#minikube image load mysql
#minikube image load jenkins

$manifestDirectory = @(
  "D:\works\react-project\docker\minikube\init"
  "D:\works\react-project\docker\minikube"
)

ForEach ($directory in $manifestDirectory) {

  Get-ChildItem -Path $directory -Filter *.yaml | ForEach-Object {
      $filePath = $_.FullName
      Write-Host "Applying manifest file: $filePath"
      
      # kubectl을 통해 파일 적용
      kubectl apply -f $filePath
  }
}

#cd "D:\works\react-project\docker\sh"
# db 설정
# .\db_secrets.bat
# .\registry_auth.bat
# mnt 설정
# .\bg_jobs.ps1

Write-Host "All manifest files from both directories applied successfully."