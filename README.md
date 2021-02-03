# serving images in spring mvc
we sometimes use controllers to serve images where images are located in the filesystem or in the application's classpath.
we can directly write the image as stream to HttpServletResponse or we can use ResponseEntity send as raw bytes with media type set image 
