# docker image
minikube image load docker-server
minikube image load docker-mysql
minikube image load react

$manifestDirectory = @(
 # "D:\works\react-project\BootTodoExample\docker\minikube",
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

# mnt 설정
CD $manifestDirectory[1]
.\bg_jobs.ps1

Write-Host "All manifest files from both directories applied successfully."