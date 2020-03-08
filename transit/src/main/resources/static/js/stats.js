let map = new Array();
let url = 'stats?lat0=38.37643137787455&lon0=-9.803304573961498&lat1=40.60602702401283&lon1=-7.12171660867034';
let list = [];

// store types names
let  types_names = ['NA', 'Accident', 'Congestion', 'Disabled Vehicle', 'Mass Transit', 'Miscellaneous', 'Other News',
    'Planned Event', 'Road Hazard', 'Construction', 'Alert', 'Weather'];

fetch(url).then(res => res.json()).then((out) => {
    for (let i = 0; i < out.length; i++) {
        let type = types_names[out[i]["type"]]

        if (map[type] == null) {
            map[type] = 0;
        }
        let tmp = parseInt(map[type]);
        map[type] = tmp + 1;
    }
    for (let k in map) {
        list.push({name: k, y: 1, z: map[k]})
    }

    Highcharts.chart('container', {
        chart: {
            type: 'variablepie'
        },
        title: {
            text: 'Number of Occurrences by Category (Portugal)'
        },
        tooltip: {
            headerFormat: '',
            pointFormat: '<span style="color:{point.color}">\u25CF</span> <b> {point.name}</b><br/> ' +
                'Occurrences: <b>{point.z}</b><br/>'
        },
        series: [{
            minPointSize: 10,
            innerSize: '20%',
            zMin: 0,
            name: 'Categories',
            data: list
        }]
    });

}).catch(err => { throw err });


