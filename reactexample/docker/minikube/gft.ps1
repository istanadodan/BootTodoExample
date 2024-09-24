param(
    [string]$sub = "",
    [string]$t = "frontend"
)

function Test-Path {
    param (
        [string]$pod,
        [string]$path
    )
    kubectl exec -it $pod -- test -d $path
    return $?
}

if ($t -eq "frontend") {
    $path ="/usr/share/nginx/html"
    } else {
    $path = "/usr/local/tomcat/webapps"
}

$result = kubectl get po | sls $t | sls "Running" | % { $_.Line.Split(" ")[0] }
echo "t=$t, path=$path, pod=$result"
if (-not $result) {
    # 방법 3: [Console]::OutputEncoding 속성 사용
    [Console]::OutputEncoding = [System.Text.Encoding]::UTF8
    # Write-Host  "서버가 올라와 있지 않습니다."
    Write-Host "Server not running"
    return
}

if (-not (Test-Path -pod $result -path $path)) {
    $path = "/"
}

if ($sub -ne "") {
    $path = $path + $sub
}

kubectl exec -it $result -- ls -al $path
