module.exports = function (grunt) {

	var DIR_DEV = "app";
	var DIR_PROD = "dist";

	grunt.initConfig({
		jshint: {
			options: {
				esversion: 6,
				bitwise: true,
				curly: true,
				asi: false
			},
			all: [DIR_DEV + '/spas/**/*.js', '!' + DIR_DEV + 'app.js']
		},
		concat: {
			dist: {
				src: [DIR_DEV + '/js/**/*.js', '!' + DIR_DEV + '/js/assets/**/*.js'],
				dest: DIR_PROD + '/js/build.min.js'
			}
		},
		uglify: {
			dist: {
				src: [DIR_PROD + '/js/build.min.js'],
				dest: DIR_PROD + '/js/build.min.js'
			}
		},
		cssmin: {
			dist: {
				src: [DIR_DEV + '/css/**/*.css'],
				dest: DIR_PROD + '/css/style.min.css'
			}
		},
		htmlmin: {
			dist: {
				expand: true,
				options: {
					removeComments: true,
					collapseWhitespace: true
				},
				src: [DIR_DEV + '/pages/**/*.html', DIR_DEV + '/index.html'],
				dest: DIR_PROD
			}
		},
		copy: {
			dist: {
				expand: true,
				src: [
					'node_modules/angular/angular.min.js',
					'node_modules/angular-route/angular-route.min.js',
					'node_modules/bootstrap/dist/css/bootstrap.min.css',
					'node_modules/bootstrap/dist/js/bootstrap.min.js',
					'node_modules/jquery/dist/jquery.min.js',
					'node_modules/angular-ui-mask/dist/mask.min.js',
					'node_modules/jquery-mask-plugin/dist/jquery.mask.min.js',

					'node_modules/bootstrap/dist/css/bootstrap.min.css',
					'node_modules/@fortawesome/fontawesome-free/css/all.min.css',
					'node_modules/magnific-popup/dist/magnific-popup.css',

					'node_modules/@fortawesome/fontawesome-free/webfonts/fa-solid-900.woff2',
					'node_modules/@fortawesome/fontawesome-free/webfonts/fa-solid-900.ttf',
				],
				dest: DIR_PROD
			},
			dev: {
				expand: true,
				src: [
					'node_modules/angular/angular.min.js',
					'node_modules/angular-route/angular-route.min.js',
					'node_modules/bootstrap/dist/js/bootstrap.bundle.min.js',
					'node_modules/jquery/dist/jquery.min.js',
					'node_modules/jquery.easing/jquery.easing.min.js',
					'node_modules/scrollreveal/dist/scrollreveal.min.js',
					'node_modules/magnific-popup/dist/jquery.magnific-popup.min.js',
					'node_modules/angular-ui-mask/dist/mask.min.js',
					'node_modules/jquery-mask-plugin/dist/jquery.mask.min.js',

					'node_modules/bootstrap/dist/css/bootstrap.min.css',
					'node_modules/@fortawesome/fontawesome-free/css/all.min.css',
					'node_modules/magnific-popup/dist/magnific-popup.css',

					'node_modules/@fortawesome/fontawesome-free/webfonts/fa-solid-900.woff2',
					'node_modules/@fortawesome/fontawesome-free/webfonts/fa-solid-900.ttf',

					DIR_DEV + '/**',
					DIR_DEV + '/fonts/**'
				],
				dest: DIR_PROD
			},
			devindex: {
				expand: true,
				src: ['index.html', 'app.js'],
				dest: DIR_PROD
			}
		},
		clean: {
			dist: {
				src: [DIR_PROD]
			}
		},
		connect: {
			server: {
				options: {
					port: 8000,
					base: DIR_PROD
				}
			}
		},
		watch: {
			dev: {
				files: [DIR_DEV + '/**/*.js', DIR_DEV + '/**/*.html', DIR_DEV + '/**/*.css', 'index.html', 'app.js'],
				tasks: ['refresh'],
				options: {
					spawn: false,
					livereload: true
				}
			},
			dist: {
				//                files:  [DIR_DEV + '/**/*.js', DIR_DEV + '/**/*.html', DIR_DEV + '/**/*.css', 'index.html'],
				options: {
					spawn: false,
					livereload: true
				}
			}
		}

	});

	grunt.loadNpmTasks('grunt-contrib-jshint');
	grunt.loadNpmTasks('grunt-contrib-concat');
	grunt.loadNpmTasks('grunt-contrib-uglify-es');
	grunt.loadNpmTasks('grunt-contrib-cssmin');
	grunt.loadNpmTasks('grunt-contrib-htmlmin');
	grunt.loadNpmTasks('grunt-contrib-copy');
	grunt.loadNpmTasks('grunt-contrib-clean');
	grunt.loadNpmTasks('grunt-contrib-connect');
	grunt.loadNpmTasks('grunt-contrib-watch');

	grunt.registerTask('default', [
		'jshint:all', 'clean:dist', 'copy:dev', 'copy:devindex', 'connect:server', 'watch:dev'
	]);

	grunt.registerTask('dist', [
		'jshint:all', 'clean:dist', 'concat:dist', 'uglify:dist',
		'cssmin:dist', 'htmlmin:dist', 'copy:dist', 'connect:server', 'watch:dist'
	]);

	grunt.registerTask('refresh', [
		'jshint:all', 'clean:dist', 'copy:dev', 'copy:devindex'
	]);

};